package com.hn.jdstore.dao;

import com.hn.jdstore.domain.entity.HanmaAddressDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao extends JpaRepository<HanmaAddressDo, Long> {
}
