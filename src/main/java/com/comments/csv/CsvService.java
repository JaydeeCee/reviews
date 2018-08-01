package com.comments.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.supercsv.cellprocessor.FmtDate;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.comments.commons.CommentExtract;

// service to convert comments and reviews to csv File
public class CsvService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(CsvService.class);
	
	@Bean
	public static void writeCsvFile(String csvFileName, List<CommentExtract> commentExtract) {
		LOGGER.debug("Now at the CSV Service");
		
		ICsvBeanWriter beanWriter = null;
		CellProcessor[] processors = new CellProcessor[] {
				new NotNull(), // username
				new FmtDate("MM/dd/yyyy"),  // date
				new NotNull(), //rating
				new NotNull(), //comment
				new NotNull(), //link
		};
		
		try {
			beanWriter = new CsvBeanWriter(new FileWriter(csvFileName),
					CsvPreference.STANDARD_PREFERENCE);
			
			String[] header = {"UserName", "Date", "Rating", "Comment", "Link"};
			beanWriter.writeHeader(header);
			
			for(CommentExtract ext: commentExtract) {
				beanWriter.write(ext, header, processors);
			}
			
			LOGGER.debug("CSV Convertion completed");
			
		}catch(IOException ex) {
			ex.printStackTrace(System.err);
			
		} finally {
			if(beanWriter != null) {
				try {
					beanWriter.close();
				} catch(IOException ex) {
					ex.printStackTrace(System.err);
				}
			}
		}
	}

}
