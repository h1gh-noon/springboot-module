package com.store.jdstore.dao;

import com.store.jdstore.entity.HanmaAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressDao extends JpaRepository<HanmaAddressEntity, Long> {
}
