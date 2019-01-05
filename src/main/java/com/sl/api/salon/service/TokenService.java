package com.sl.api.salon.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sl.api.salon.mapper.SlBrandMapper;
import com.sl.api.salon.mapper.SlUserMapper;
import com.sl.api.salon.mapper.SlUserWechatMapper;
import com.sl.api.salon.model.SToken;
import com.sl.api.salon.model.UserForToken;
import com.sl.api.salon.model.UserType;
import com.sl.api.salon.model.db.SlUser;
import com.sl.api.salon.model.db.SlUserWechat;
import com.sl.api.salon.util.Constant;
import com.zeasn.common.daemon.Daemon;
import com.zeasn.common.daemon.IWriteBack;
import com.zeasn.common.feign.api.SnowFlakeApi;
import com.zeasn.common.redis.ObjectRedisApi;

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
	private Daemon daemon;
	
	public String getRedirect(String domain, UserForToken user, String tokenStr){
		UserType tp = UserType.valueOf(user.getRoleId());
		switch(tp){
			case BARBER:
				return String.format("%s/pages/barber?token=%s", domain, tokenStr);
				
			case MEMBER:
				return String.format("%s/pages/member?token=%s", domain, tokenStr);
			
			default:
				return this.getRedirectForError(domain);
		}
	}
	
	public String getRedirectForError(String domain){
		return domain + "/error";
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
		
		SlUser user = new SlUser(uId, brandId, nickName, phoneNumber, null, "", UserType.MEMBER.toString(), 1, 0, null, avatarUrl, ts, ts);
		
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
	
	public String register(String wechatUnionId){
		List<UserForToken> list = this.slUserMapper.getUserFromWechat(wechatUnionId);
		
		if(CollectionUtils.isNotEmpty(list)){
			return this.register(list.get(0));
			
		}else{
			throw new IllegalArgumentException("get token failed");
		}
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
