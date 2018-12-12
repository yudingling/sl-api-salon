package com.sl.api.salon.service;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sl.api.salon.mapper.TokenInfoMapper;
import com.sl.api.salon.model.SToken;
import com.sl.api.salon.model.token.UserForToken;
import com.sl.api.salon.util.Constant;
import com.zeasn.common.feign.api.SnowFlakeApi;
import com.zeasn.common.redis.ObjectRedisApi;

@Service
public class TokenService {
	private static final long REDIS_EXPIRESECONDS_TOKEN = 86400; //expire in 24 hours
	
	@Autowired
	private ObjectRedisApi objRedisApi;
	@Autowired
	private TokenInfoMapper tokenInfoMapper;
	@Autowired
	private SnowFlakeApi snowFlakeApi;
	
	public SToken getToken(String token){
		String key = String.format("%s%s", Constant.REDIS_PREFIX_TOKEN, token);
		return this.objRedisApi.get(key);
	}
	
	public String register(String wechatUnionId){
		List<UserForToken> list = this.tokenInfoMapper.getUserFromWechat(wechatUnionId);
		
		if(CollectionUtils.isNotEmpty(list)){
			String tokenStr = this.snowFlakeApi.nextStringId();
			String key = String.format("%s%s", Constant.REDIS_PREFIX_TOKEN, tokenStr);
			
			SToken tk = new SToken(list.get(0));
			this.objRedisApi.set(key, tk, REDIS_EXPIRESECONDS_TOKEN, 600);
			
			return tokenStr;
			
		}else{
			throw new IllegalArgumentException("get token failed");
		}
	}
}
