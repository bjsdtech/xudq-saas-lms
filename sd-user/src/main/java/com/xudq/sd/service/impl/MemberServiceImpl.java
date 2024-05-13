package com.xudq.sd.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudq.sd.entity.Member;
import com.xudq.sd.mapper.MemberMapper;
import com.xudq.sd.service.IMemberService;
import org.springframework.stereotype.Service;

/**
 * 用户服务类实现
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements IMemberService {

}