package com.sugon.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Const {
	
	@Value("${image_savepath}")
	public String IMAGE_SAVEPATH;
	
	@Value("${th_exp_default}")
	public String EXP_DEFAULT;
	
	@Value("${menu_prefix}")
	public String MENU_PREFIX;

}
