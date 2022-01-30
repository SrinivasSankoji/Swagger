package com.isuite.rjil.iagent.sso;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;



public class ObjectSerializationUtils {

	private static final Logger LOGGER = Logger
			.getLogger(ObjectSerializationUtils.class);

	public static void writeObject(File file, Set<String> data) {
		ObjectOutputStream oos = null;
		FileOutputStream fos = null;
		try {

			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);

			oos.writeObject(data);

			oos.flush();
			oos.close();

		} catch (FileNotFoundException fnfExp) 
		{
			LOGGER.error(fnfExp.getMessage(),fnfExp);
		} catch (IOException ioExp) 
		{
			LOGGER.error(ioExp.getMessage(),ioExp);
		} finally {
			try {

				if (null != oos) {
					oos.flush();
					oos.close();
				}
				if (null != fos) {
					fos.close();
				}
			} catch (Exception exp) 
			{
				LOGGER.error(exp.getMessage(),exp);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public static Set<String> readObject(File file) {
		ObjectInputStream ois = null;
		FileInputStream fis = null;
		Set<String> data = new HashSet<String>();
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);

			data = (HashSet<String>) ois.readObject();

			ois.close();

		} catch (FileNotFoundException fnfExp) {
			LOGGER.error(fnfExp.getMessage(),fnfExp);
		} catch (IOException ioExp) {
			LOGGER.error(ioExp.getMessage(),ioExp);
		} catch (ClassNotFoundException cnfExp) {
			LOGGER.error(cnfExp.getMessage(),cnfExp);
		} finally {
			try {

				if (null != ois) {
					ois.close();
				}
				if (null != fis) {
					fis.close();
				}

			} catch (Exception exp) {
				LOGGER.error(exp.getMessage(),exp);
			}
		}

		return data;
	}

	public static File fileExistance(File fileName) throws IOException {
		if (!fileName.exists()) {
			fileName.createNewFile();
		}
		return fileName;
	}

	

}
