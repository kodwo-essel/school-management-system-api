package com.schoolmgt.auth.usermanagement.service;

import com.schoolmgt.auth.base.service.BaseService;
import com.schoolmgt.auth.usermanagement.dto.AdminRegistrationDTO;
import com.schoolmgt.auth.usermanagement.entity.Admin;
import com.schoolmgt.auth.usermanagement.entity.SuperAdmin;

public interface SuperAdminService extends BaseService<SuperAdmin> {
    // register an admin
    Admin registerSchoolAdmin(AdminRegistrationDTO admin);

}
