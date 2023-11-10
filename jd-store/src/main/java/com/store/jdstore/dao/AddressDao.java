package com.store.jdstore.dao;

import com.store.jdstore.entity.HanmaAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressDao extends JpaRepository<HanmaAddressEntity, Long> {
}
