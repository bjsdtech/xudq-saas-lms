package com.xudq.sd.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xudq.sd.entity.AddressBook;
import com.xudq.sd.mapper.AddressBookMapper;
import com.xudq.sd.service.IAddressBookService;
import org.springframework.stereotype.Service;

/**
 * 地址簿服务类实现
 */
@Service
public class AddressBookServiceImpl extends ServiceImpl<AddressBookMapper, AddressBook> implements IAddressBookService {

}