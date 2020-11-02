package com.service.common.repository;

import com.service.common.domain.Heir;
import com.service.common.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeirRepository extends JpaRepository<Heir, Long> {

    public List<Heir> getHeirByOwner(Owner owner);
}
