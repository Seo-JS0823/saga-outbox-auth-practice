package com.msa.auth.domain.port.out;

import com.msa.auth.domain.model.User;
import com.msa.auth.domain.request.UserCommandRequest.SignupRequest;

public interface UserCommandPort {

	User save(SignupRequest userCreateRequest);
}
