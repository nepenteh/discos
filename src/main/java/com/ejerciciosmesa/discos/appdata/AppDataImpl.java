package com.ejerciciosmesa.discos.appdata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

import org.springframework.stereotype.Component;


@Component
public class AppDataImpl  implements AppData {

	private String name; 
	private String author; 
	private int year; 
	private String web; 
	private String webURL; 
	private TreeMap<String,GeneralOption> generalOptions; 
	private ArrayList<GeneralOption> sortedGeneralOptions;
			
	public AppDataImpl() {
		name = "Colección de Discos";
		author = "";
		year = 2023;
		web = "";
		webURL = "";
		
		generalOptions = new TreeMap<>();
		sortedGeneralOptions = new ArrayList<>();
		int order=0;
		
		GeneralOption opDisco = new GeneralOption(order,"DISCO","Disco","/disco/list","LIST");

opDisco.addScreen("LIST","Disco (List)");
opDisco.addScreen("CREATE","Disco (Create)");
opDisco.addScreen("UPDATE","Disco (Update)");
opDisco.addScreen("VIEW","Disco (View)");
opDisco.addScreen("VIEWIMG","Disco - View Image");

opDisco.setEmptyMessage("No data");

generalOptions.put("DISCO",opDisco);
sortedGeneralOptions.add(opDisco);

order++;

GeneralOption opBanda = new GeneralOption(order,"BANDA","Banda","/banda/list","LIST");

opBanda.addScreen("LIST","Banda (List)");
opBanda.addScreen("CREATE","Banda (Create)");
opBanda.addScreen("UPDATE","Banda (Update)");
opBanda.addScreen("VIEW","Banda (View)");
opBanda.addScreen("VIEWIMG","Banda - View Image");

opBanda.setEmptyMessage("No data");

generalOptions.put("BANDA",opBanda);
sortedGeneralOptions.add(opBanda);

order++;

GeneralOption opInstrumentomusical = new GeneralOption(order,"INSTRUMENTOMUSICAL","InstrumentoMusical","/instrumentomusical/list","LIST");

opInstrumentomusical.addScreen("LIST","InstrumentoMusical (List)");
opInstrumentomusical.addScreen("CREATE","InstrumentoMusical (Create)");
opInstrumentomusical.addScreen("UPDATE","InstrumentoMusical (Update)");
opInstrumentomusical.addScreen("VIEW","InstrumentoMusical (View)");
opInstrumentomusical.addScreen("VIEWIMG","InstrumentoMusical - View Image");

opInstrumentomusical.setEmptyMessage("No data");

generalOptions.put("INSTRUMENTOMUSICAL",opInstrumentomusical);
sortedGeneralOptions.add(opInstrumentomusical);

order++;


		
		Collections.sort(sortedGeneralOptions);
		
	}
	
	
	@Override
	public String getName() {
		return name;
	}
	
	
	@Override
	public String getAuthor() {
		return author;
	}

	
	@Override
	public int getYear() {
		return year;
	}

	
	@Override
	public String getWeb() {
		return web;
	}
	
	
	@Override
	public String getWebUrl() {
		return webURL;
	}

	
	@Override
	public String getAuthorship() {
		String authorship = "";
		if(!author.isBlank() || !web.isBlank()) {
			authorship += author+" "+year;
			if(!web.isBlank()) authorship += " - "+web;
		}		
		return authorship.trim();
	}
	
	
	@Override
	public TreeMap<String, GeneralOption> getGeneralOptions() {
		return generalOptions;
	}
	
	@Override
	public ArrayList<GeneralOption> getSortedGeneralOptions() {
		return sortedGeneralOptions;
	}
	
	@Override
	public boolean isMainScreen(String optionCode, String screenCode) {
		return generalOptions.get(optionCode).getMainScreenCode().equals(screenCode);
	}
	
	@Override
	public String getMainScreenName(String optionCode) {
		return generalOptions.get(optionCode).getMainScreenName();
	}
	
	@Override
	public String getMainScreenLink(String optionCode) {
		return generalOptions.get(optionCode).getLink();
	}
	
	@Override
	public String getScreenName(String optionCode, String screenCode) {
		return generalOptions.get(optionCode).getScreen(screenCode);
	}

	@Override
	public String getEmptyMessage(String optionCode) {
		return generalOptions.get(optionCode).getEmptyMessage();
	}
		
}
