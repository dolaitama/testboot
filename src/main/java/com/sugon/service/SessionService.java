package com.sugon.service;

import com.sugon.domain.Staff;

public interface SessionService {

	void refreshSession(Staff staff) throws Exception;

}
