package com.sl.api.salon.service;

import java.io.IOException;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;

import com.alibaba.fastjson.JSON;
import com.github.andrewoma.dexx.collection.Map;
import com.sl.api.salon.mapper.SlBrandMapper;
import com.sl.api.salon.mapper.SlBrandWechatMapper;
import com.sl.api.salon.mapper.SlUserMapper;
import com.sl.api.salon.mapper.SlUserWechatMapper;
import com.sl.common.model.SToken;
import com.sl.common.model.UserForToken;
import com.sl.common.model.UserType;
import com.sl.common.model.db.SlBrandWechat;
import com.sl.common.model.db.SlUser;
import com.sl.common.model.db.SlUserWechat;
import com.sl.common.util.Constant;
import com.zeasn.common.daemon.Daemon;
import com.zeasn.common.daemon.IWriteBack;
import com.zeasn.common.feign.api.SnowFlakeApi;
import com.zeasn.common.model.NotHttp200Exception;
import com.zeasn.common.redis.ObjectRedisApi;
import com.zeasn.common.util.HttpClientUtil;

@Service
public class TokenService implements IWriteBack<Object> {
	private static final long REDIS_EXPIRESECONDS_TOKEN = 86400; //expire in 24 hours
	
	@Autowired
	private ObjectRedisApi objRedisApi;
	@Autowired
	private SlUserMapper slUserMapper;
	@Autowired
	private SlUserWechatMapper slUserWechatMapper;
	@Autowired
	private SlBrandMapper slBrandMapper;
	@Autowired
	private SnowFlakeApi snowFlakeApi;
	@Autowired
	private SlBrandWechatMapper brandWechatMapper;
	@Autowired
	private Daemon daemon;
	
	/**
	 * @return reutrn null if unionid not found
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public String getWechatUnionId(String bdId, String code){
		Example example = new Example(SlBrandWechat.class);
	    example.createCriteria().andEqualTo("bdId", bdId);
	    
	    List<SlBrandWechat> list = this.brandWechatMapper.selectByExample(example);
	    if(CollectionUtils.isNotEmpty(list)){
	    	SlBrandWechat bw = list.get(0);
	    	
	    	String url = String.format("https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code", 
	    			bw.getBdwAppid(), bw.getBdwSecret(), code);
	    	
	    	try {
				String json = HttpClientUtil.httpGet(url);
				
				Map map = JSON.parseObject(json, Map.class);
				String unionid = null;
				
				if(map.containsKey("errcode")){
					String errorCode = map.get("errcode").toString();
					if(errorCode.equals("0")){
						unionid = (String) map.get("unionid");
					}
				}
				
				return unionid;
				
			} catch (IOException | NotHttp200Exception e) {
				return null;
			}
	    }
	    
	    return null;
	}
	
	public String getRedirect(String domain, UserForToken user, String tokenStr){
		UserType tp = UserType.valueOf(user.getRoleId());
		switch(tp){
			case BARBER:
				return String.format("%s/pages/barber?token=%s", domain, tokenStr);
				
			case MEMBER:
				return String.format("%s/pages/member?token=%s", domain, tokenStr);
			
			default:
				return domain + "/error";
		}
	}
	
	public SToken getToken(String token){
		String key = String.format("%s%s", Constant.REDIS_PREFIX_TOKEN, token);
		return this.objRedisApi.get(key);
	}
	
	public UserForToken getUser(String wechatUnionId){
		List<UserForToken> list = this.slUserMapper.getUserFromWechat(wechatUnionId);
		
		return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
	}
	
	@Transactional(rollbackFor = Exception.class)
	public UserForToken registerUser(String brandId, String unionId, String nickName, String avatarUrl, String phoneNumber){
		if(!this.slBrandMapper.existsWithPrimaryKey(brandId)){
			return null;
		}
		
		Long uId = this.snowFlakeApi.nextId();
		long ts = System.currentTimeMillis();
		
		SlUser user = new SlUser(uId, brandId, nickName, unionId, phoneNumber, null, "", UserType.MEMBER.toString(), 1, 0, null, avatarUrl, ts, ts);
		
		if(this.slUserMapper.insert(user) == 1){
			SlUserWechat uw = new SlUserWechat(this.snowFlakeApi.nextId(), unionId, uId, ts, ts);
			
			this.slUserWechatMapper.insert(uw);
			
			return new UserForToken(uId, brandId, UserType.MEMBER.toString(), avatarUrl);
		}
		
		return null;
	}
	
	public void updateUserAvatar(long uId, String avatarUrl){
		this.daemon.protect(this, new SlUser(uId, avatarUrl, System.currentTimeMillis()));
	}
	
	public String register(UserForToken user){
		String tokenStr = this.snowFlakeApi.nextStringId();
		String key = String.format("%s%s", Constant.REDIS_PREFIX_TOKEN, tokenStr);
		
		SToken tk = new SToken(user);
		this.objRedisApi.set(key, tk, REDIS_EXPIRESECONDS_TOKEN, 600);
		
		return tokenStr;
	}
	
	@Override
	public boolean writeBack(Object data) {
		if(data instanceof SlUser){
			this.slUserMapper.updateByPrimaryKeySelective((SlUser)data);
		}
		
		return true;
	}
}
