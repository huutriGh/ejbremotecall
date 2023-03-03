package com.example.ejbremotecall;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.aptech.EmployeeBean;
import org.aptech.entities.Employee;
import org.aptech.remoteinterface.EmployeeRemote;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.aptech.entities.Address;


@SpringBootApplication
public class EjbremotecallApplication implements CommandLineRunner {
	
	public static void main(String[] args) {
		SpringApplication.run(EjbremotecallApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		try{
			Properties jndiProps = new Properties();
			jndiProps.put(Context.INITIAL_CONTEXT_FACTORY,"org.wildfly.naming.client.WildFlyInitialContextFactory");
			jndiProps.put(Context.PROVIDER_URL,"http-remoting://localhost:8080");
			Context context = new InitialContext(jndiProps);
			EmployeeRemote employeeBean = (EmployeeRemote) context.lookup("ejb:company/company-ejb/EmployeeEJB!org.aptech.remoteinterface.EmployeeRemote");
	
			Employee em = new Employee();
			em.setFullName("Nguyễn Hữu Trí");
			em.setBirthday(Date.from(LocalDate.of(1990, 06, 01).atStartOfDay(ZoneId.systemDefault()).toInstant()));
			Address address = new Address();
			address.setCity("Hồ Chí Minh");
			address.setWard("Phường 11");
			address.setDistrict("Quận 3");
			address.setEmployee(em);
			em.setAddress(address);
			employeeBean.addEmployee(em);
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	

	}

}
