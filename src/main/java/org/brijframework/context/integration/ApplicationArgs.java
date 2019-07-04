package org.brijframework.context.integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationArgs {
 
	String[] varArgs;
	
	List<String> nonParams;
	
	Map<String, Object> withParams;
	
	public List<String> getNonParams() {
		if(nonParams==null) {
			nonParams=new ArrayList<>();
		}
		return nonParams;
	}
	
	public Map<String, Object> getWithParams() {
		if(withParams==null) {
			withParams=new HashMap<>();
		}
		return withParams;
	}
	
	public String[] getVarArgs() {
		if(varArgs==null) {
			varArgs=new String[0];
		}
		return varArgs;
	}
	
	public void setVarArgs(String... varArgs) {
		this.varArgs = varArgs;
	}
	
	public void initial(String[] varArgs) {
		if (varArgs == null) {
			return;
		}
		this.setVarArgs(varArgs);
		for (String arg : varArgs) {
			String[] varArg = arg.split("=");
			if (varArg.length == 1) {
				this.getNonParams().add(arg.trim());
			} else if (varArg.length == 2) {
				this.getWithParams().put(varArg[0].trim(), varArg[1].trim());
			}
		}
	}
}
