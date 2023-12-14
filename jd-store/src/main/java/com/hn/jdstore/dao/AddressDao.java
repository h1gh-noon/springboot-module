package com.hn.jdstore.dao;

import com.hn.jdstore.domain.entity.AddressDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao extends JpaRepository<AddressDo, Long> {
}
