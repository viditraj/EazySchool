package com.education.School.audit;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImpl")
public class AuditAwareImpl implements AuditorAware<String> {

    //Function to get the details of current logged in user. If the user is not logged in then this function will return NULL
    //that is why we are using Optional<String>
    //This information will be used by Auditior to populate fields like createdBy and modifiedBy present in BaseEntity
    @Override
    public Optional<String> getCurrentAuditor(){
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
