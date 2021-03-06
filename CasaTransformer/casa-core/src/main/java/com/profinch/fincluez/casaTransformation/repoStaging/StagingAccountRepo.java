package com.profinch.fincluez.casaTransformation.repoStaging;

import com.profinch.fincluez.casaTransformation.staging.StagingAccount;
import com.profinch.fincluez.casaTransformation.staging.StagingAccountCK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StagingAccountRepo extends JpaRepository<StagingAccount, StagingAccountCK> {
}
