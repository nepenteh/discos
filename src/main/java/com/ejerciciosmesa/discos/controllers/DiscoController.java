package com.ejerciciosmesa.discos.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.ejerciciosmesa.discos.util.paginator.PageRender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.springframework.validation.BindingResult;
import javax.validation.Valid;
import org.springframework.web.bind.support.SessionStatus;



import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import com.ejerciciosmesa.discos.models.services.UploadService;





import com.ejerciciosmesa.discos.appdata.AppData;
import com.ejerciciosmesa.discos.models.entities.Disco;
import com.ejerciciosmesa.discos.models.services.DiscoService;




@Controller
@SessionAttributes("disco")
@RequestMapping("/disco")
public class DiscoController {

	private final AppData appData;
	private final DiscoService discoService;
	
	
	
	
	
	private final UploadService uploadService;

		
	public static final String OPGEN = "DISCO"; 
	
	public DiscoController(UploadService uploadService,
										 
										 
									     DiscoService discoService,
									     AppData applicationData
		   
		   		 
			) {
		this.appData = applicationData;
		this.discoService = discoService;
		
		
		

		this.uploadService = uploadService;

	}

		
	
	@GetMapping({"","/","/list"})
	public String list(@RequestParam(name="page", defaultValue="0") int page, Model model) {
	
		fillApplicationData(model,"LIST");
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Disco> pageDisco = discoService.findAll(pageRequest); 
		PageRender<Disco> paginator = new PageRender<>("/disco/list",pageDisco,5);
		

		model.addAttribute("numdisco", discoService.count());
		model.addAttribute("listdisco", pageDisco);
		model.addAttribute("paginator",paginator);
		
		return "disco/list";
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		Disco disco = new Disco();		
		model.addAttribute("disco",disco);
		
		fillApplicationData(model,"CREATE");
		
		return "disco/form";
	}
	
	@GetMapping("/form/{id}")
	public String form(@PathVariable Long id, Model model, RedirectAttributes flash) {
		Disco disco = discoService.findOne(id);
		if(disco==null) {
			flash.addFlashAttribute("error","Data not found");
			return "redirect:/disco/list";
		}
		
		model.addAttribute("disco", disco);
		
		fillApplicationData(model,"UPDATE");
		
		return "disco/form";
	}
	
	
	@PostMapping("/form")
	@Secured("ROLE_ADMIN")
	public String form(@Valid Disco disco,  
			           BindingResult result, 
					   
					   Model model,
					   @RequestAttribute("file") MultipartFile caratula_formname,
@RequestParam("caratulaImageText") String caratulaImageText,
@RequestParam("caratulaImageTextOld") String caratulaImageTextOld,

					   RedirectAttributes flash,
					   SessionStatus status) {
		
		if(disco.getId()==null)
			fillApplicationData(model,"CREATE");
		else
			fillApplicationData(model,"UPDATE");
		
		String msg = (disco.getId()==null) ? "Creation successful" : "Update successful";
		
		if(result.hasErrors()) {
			return "disco/form";
		}
		
		if(!caratula_formname.isEmpty())
	AddUpdateImageCaratula(caratula_formname,disco);
else {
	if(caratulaImageText.isEmpty() && !(caratulaImageTextOld.isEmpty())) {
		uploadService.delete(caratulaImageTextOld);
		disco.setCaratula(null);
	}
}



		
		
		
		discoService.save(disco);
		status.setComplete();
		flash.addFlashAttribute("success",msg);
		return "redirect:/disco/list";
	}
	
	
	private void AddUpdateImageCaratula(MultipartFile image, Disco disco) {
					
			if(disco.getId()!=null &&
			   disco.getId()>0 && 
			   disco.getCaratula()!=null &&
			   disco.getCaratula().length() > 0) {
			
				uploadService.delete(disco.getCaratula());
			}
			
			String uniqueName = null;
			try {
				uniqueName = uploadService.copy(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			disco.setCaratula(uniqueName);
		
	}

	

	@Secured("ROLE_ADMIN")
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes flash) {
		
		if(id>0) { 			
			Disco disco = discoService.findOne(id);
			
			if(disco != null) {
				
		/* Only if there is required relation with this entity */			
				
		
		/* Only if there is no required relation with this entity, or there is a
		 * required relation but no entity related at the moment, (checked before) */
				
		
		/* Relations revised, the entity can be removed */
				discoService.remove(id);
			} else {
				flash.addFlashAttribute("error","Data not found");
				return "redirect:/disco/list";
			}
			
			if(disco.getCaratula()!=null)
	uploadService.delete(disco.getCaratula());

						
			flash.addFlashAttribute("success","Deletion successful");
		}
		
		return "redirect:/disco/list";
	}
	
	@GetMapping("/view/{id}")
	public String view(@PathVariable Long id, Model model, RedirectAttributes flash) {

		if (id > 0) {
			Disco disco = discoService.findOne(id);

			if (disco == null) {
				flash.addFlashAttribute("error", "Data not found");
				return "redirect:/disco/list";
			}

			model.addAttribute("disco", disco);
			fillApplicationData(model, "VIEW");
			return "disco/view";
			
		}

		return "redirect:/disco/list";
	}
	
	
	@GetMapping("/viewimg/{id}/{imageField}")
	public String viewimg(@PathVariable Long id, @PathVariable String imageField, Model model, RedirectAttributes flash) {

		if (id > 0) {
			Disco disco = discoService.findOne(id);

			if (disco == null) {
				flash.addFlashAttribute("error", "Data not found");
				return "redirect:/disco/list";
			}

			model.addAttribute("disco", disco);
			fillApplicationData(model, "VIEWIMG");
			model.addAttribute("backOption",true);
			model.addAttribute("imageField",imageField);
			
			return "disco/viewimg";
			
		}

		return "redirect:/disco/list";
	}
	
	
	
	
	private void fillApplicationData(Model model, String screen) {
		model.addAttribute("applicationData",appData);
		model.addAttribute("optionCode",OPGEN);
		model.addAttribute("screen",screen);
	}
	
		
}
