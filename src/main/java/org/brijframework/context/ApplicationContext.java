package org.brijframework.context;

import java.io.File;
import java.util.Arrays;

import org.brijframework.asm.context.AbstractBootstrapContext;
import org.brijframework.asm.factories.FileFactory;
import org.brijframework.config.EnvConfigration;
import org.brijframework.context.config.ApplicationConfigration;
import org.brijframework.support.config.BootStrapConfig;
import org.brijframework.support.enums.ResourceType;
import org.brijframework.support.util.SupportUtil;
import org.brijframework.util.objects.PropertiesUtil;
import org.brijframework.util.reflect.AnnotationUtil;
import org.brijframework.util.reflect.InstanceUtil;
import org.brijframework.util.reflect.ReflectionUtils;
import org.brijframework.util.resouces.YamlUtil;
import static org.brijframework.context.constants.ApplicationConstants.*;

public class ApplicationContext extends AbstractBootstrapContext {
	
	private EnvConfigration configration;
	
	
	public ApplicationContext() {
		this.loadConfig();
		this.init();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void init() {
		try {
			ReflectionUtils.getClassListFromExternal().forEach(cls -> {
				if (ModuleContext.class.isAssignableFrom(cls) && InstanceUtil.isAssignable(cls)) {
					register((Class<? extends ModuleContext>) cls);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ReflectionUtils.getClassListFromInternal().forEach(cls -> {
				if (ModuleContext.class.isAssignableFrom(cls) && InstanceUtil.isAssignable(cls)) {
					register((Class<? extends ModuleContext>) cls);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void startup() {
		System.err.println("=============================ApplicationContext startup==============================");
		SupportUtil.getDepandOnSortedClassList(getClassList()).forEach((Context) ->{ loadContext(Context);});
		System.err.println("=============================ApplicationContext started==============================");
	}

	@Override
	public void destory() {
		System.err.println("=============================ApplicationContext startup==============================");
		SupportUtil.getDepandOnSortedClassList(getClassList()).forEach((Context) ->{ destoryContext(Context);});
		System.err.println("=============================ApplicationContext started==============================");

	}
	
	
	protected void loadConfig() {
		System.err.println("=============================ApplicationConfig Setups==============================");
		
		EnvConfigration configration=getConfigration();
		configration.getProperties().putAll(System.getProperties());
		findAnnotationConfig(configration);
		findFileLocateConfig(configration);
		if(!configration.getProperties().containsKey(APPLICATION_BOOTSTRAP_CONFIG_LOCATION)) {
			configration.getProperties().put(APPLICATION_BOOTSTRAP_CONFIG_LOCATION, "/");
		}
		if(!configration.getProperties().containsKey(APPLICATION_BOOTSTRAP_CONFIG_FILENAME)) {
			configration.getProperties().put(APPLICATION_BOOTSTRAP_CONFIG_FILENAME,APPLICATION_BOOTSTRAP_CONFIG_FILES );
		}
		loadFileLocateConfig(configration);
	}
	
	
	protected void findAnnotationConfig(EnvConfigration configration) {
		if(configration.getProperties().containsKey(APPLICATION_BOOTSTRAP_CONFIG_LOCATION)|| configration.getProperties().containsKey(APPLICATION_BOOTSTRAP_CONFIG_FILENAME)) {
			return;
		}
		try {
			ReflectionUtils.getClassListFromInternal().forEach(cls -> {
				if (cls.isAnnotationPresent(BootStrapConfig.class)) {
					BootStrapConfig config=(BootStrapConfig) AnnotationUtil.getAnnotation(cls, BootStrapConfig.class);
					configration.getProperties().put(APPLICATION_BOOTSTRAP_CONFIG_LOCATION, config.location());
					configration.getProperties().put(APPLICATION_BOOTSTRAP_CONFIG_FILENAME, config.filename());
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void findFileLocateConfig(EnvConfigration configration) {
		if(configration.getProperties().containsKey(APPLICATION_BOOTSTRAP_CONFIG_LOCATION)|| configration.getProperties().containsKey(APPLICATION_BOOTSTRAP_CONFIG_FILENAME)) {
			return;
		}
		try {
			FileFactory.getResources(Arrays.asList(APPLICATION_BOOTSTRAP_CONFIG_FILES.split("\\|"))).forEach(file -> {
				configration.getProperties().put(APPLICATION_BOOTSTRAP_CONFIG_LOCATION, file.getPath());
				configration.getProperties().put(APPLICATION_BOOTSTRAP_CONFIG_FILENAME, file.getName());
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	protected void loadFileLocateConfig(EnvConfigration configration) {
		try {
			String path=(String) configration.getProperties().get(APPLICATION_BOOTSTRAP_CONFIG_LOCATION);
			File filePath=new File(path);
			System.err.println(APPLICATION_BOOTSTRAP_CONFIG_LOCATION+"="+path);
			System.err.println(APPLICATION_BOOTSTRAP_CONFIG_FILENAME+"="+configration.getProperties().get(APPLICATION_BOOTSTRAP_CONFIG_FILENAME));
			if(!filePath.exists()) {
				System.err.println("Env configration file not found.");
				return ;
			}
			if(filePath.toString().endsWith(ResourceType.PROP)) {
				configration.getProperties().putAll(PropertiesUtil.getProperties(filePath));
			}
			if(filePath.toString().endsWith(ResourceType.YML)||filePath.toString().endsWith(ResourceType.YAML)) {
				configration.getProperties().putAll(YamlUtil.getHashMap(filePath));
			}
			this.getProperties().putAll(configration.getProperties());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Override
	public EnvConfigration getConfigration() {
		if(this.configration==null) {
			this.configration=new ApplicationConfigration();
		}
		return this.configration;
	}

}
