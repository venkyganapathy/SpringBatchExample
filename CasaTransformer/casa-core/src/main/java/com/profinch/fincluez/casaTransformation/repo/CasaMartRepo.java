package com.profinch.fincluez.casaTransformation.repo;

import com.profinch.fincluez.casaTransformation.mart.CasaMart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CasaMartRepo extends JpaRepository<CasaMart,String> {
}
