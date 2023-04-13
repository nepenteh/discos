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
import com.ejerciciosmesa.discos.models.entities.InstrumentoMusical;
import com.ejerciciosmesa.discos.models.services.InstrumentoMusicalService;




@Controller
@SessionAttributes("instrumentoMusical")
@RequestMapping("/instrumentomusical")
public class InstrumentoMusicalController {

	private final AppData appData;
	private final InstrumentoMusicalService instrumentoMusicalService;
	
	
	
	
	
	private final UploadService uploadService;

		
	public static final String OPGEN = "INSTRUMENTOMUSICAL"; 
	
	public InstrumentoMusicalController(UploadService uploadService,
										 
										 
									     InstrumentoMusicalService instrumentoMusicalService,
									     AppData applicationData
		   
		   		 
			) {
		this.appData = applicationData;
		this.instrumentoMusicalService = instrumentoMusicalService;
		
		
		

		this.uploadService = uploadService;

	}

		
	
	@GetMapping({"","/","/list"})
	public String list(@RequestParam(name="page", defaultValue="0") int page, Model model) {
	
		fillApplicationData(model,"LIST");
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<InstrumentoMusical> pageInstrumentoMusical = instrumentoMusicalService.findAll(pageRequest); 
		PageRender<InstrumentoMusical> paginator = new PageRender<>("/instrumentomusical/list",pageInstrumentoMusical,5);
		

		model.addAttribute("numinstrumentoMusical", instrumentoMusicalService.count());
		model.addAttribute("listinstrumentoMusical", pageInstrumentoMusical);
		model.addAttribute("paginator",paginator);
		
		return "instrumentomusical/list";
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		InstrumentoMusical instrumentoMusical = new InstrumentoMusical();		
		model.addAttribute("instrumentoMusical",instrumentoMusical);
		
		fillApplicationData(model,"CREATE");
		
		return "instrumentomusical/form";
	}
	
	@GetMapping("/form/{id}")
	public String form(@PathVariable Long id, Model model, RedirectAttributes flash) {
		InstrumentoMusical instrumentoMusical = instrumentoMusicalService.findOne(id);
		if(instrumentoMusical==null) {
			flash.addFlashAttribute("error","Data not found");
			return "redirect:/instrumentoMusical/list";
		}
		
		model.addAttribute("instrumentoMusical", instrumentoMusical);
		
		fillApplicationData(model,"UPDATE");
		
		return "instrumentomusical/form";
	}
	
	
	@PostMapping("/form")
	@Secured("ROLE_ADMIN")
	public String form(@Valid InstrumentoMusical instrumentoMusical,  
			           BindingResult result, 
					   
					   Model model,
					   @RequestAttribute("file") MultipartFile foto_formname,
@RequestParam("fotoImageText") String fotoImageText,
@RequestParam("fotoImageTextOld") String fotoImageTextOld,

					   RedirectAttributes flash,
					   SessionStatus status) {
		
		if(instrumentoMusical.getId()==null)
			fillApplicationData(model,"CREATE");
		else
			fillApplicationData(model,"UPDATE");
		
		String msg = (instrumentoMusical.getId()==null) ? "Creation successful" : "Update successful";
		
		if(result.hasErrors()) {
			return "instrumentomusical/form";
		}
		
		if(!foto_formname.isEmpty())
	AddUpdateImageFoto(foto_formname,instrumentoMusical);
else {
	if(fotoImageText.isEmpty() && !(fotoImageTextOld.isEmpty())) {
		uploadService.delete(fotoImageTextOld);
		instrumentoMusical.setFoto(null);
	}
}



		
		
		
		instrumentoMusicalService.save(instrumentoMusical);
		status.setComplete();
		flash.addFlashAttribute("success",msg);
		return "redirect:/instrumentomusical/list";
	}
	
	
	private void AddUpdateImageFoto(MultipartFile image, InstrumentoMusical instrumentoMusical) {
					
			if(instrumentoMusical.getId()!=null &&
			   instrumentoMusical.getId()>0 && 
			   instrumentoMusical.getFoto()!=null &&
			   instrumentoMusical.getFoto().length() > 0) {
			
				uploadService.delete(instrumentoMusical.getFoto());
			}
			
			String uniqueName = null;
			try {
				uniqueName = uploadService.copy(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			instrumentoMusical.setFoto(uniqueName);
		
	}

	

	@Secured("ROLE_ADMIN")
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes flash) {
		
		if(id>0) { 			
			InstrumentoMusical instrumentoMusical = instrumentoMusicalService.findOne(id);
			
			if(instrumentoMusical != null) {
				
		/* Only if there is required relation with this entity */			
				
		
		/* Only if there is no required relation with this entity, or there is a
		 * required relation but no entity related at the moment, (checked before) */
				
		
		/* Relations revised, the entity can be removed */
				instrumentoMusicalService.remove(id);
			} else {
				flash.addFlashAttribute("error","Data not found");
				return "redirect:/instrumentomusical/list";
			}
			
			if(instrumentoMusical.getFoto()!=null)
	uploadService.delete(instrumentoMusical.getFoto());

						
			flash.addFlashAttribute("success","Deletion successful");
		}
		
		return "redirect:/instrumentomusical/list";
	}
	
	@GetMapping("/view/{id}")
	public String view(@PathVariable Long id, Model model, RedirectAttributes flash) {

		if (id > 0) {
			InstrumentoMusical instrumentoMusical = instrumentoMusicalService.findOne(id);

			if (instrumentoMusical == null) {
				flash.addFlashAttribute("error", "Data not found");
				return "redirect:/instrumentomusical/list";
			}

			model.addAttribute("instrumentoMusical", instrumentoMusical);
			fillApplicationData(model, "VIEW");
			return "instrumentomusical/view";
			
		}

		return "redirect:/instrumentomusical/list";
	}
	
	
	@GetMapping("/viewimg/{id}/{imageField}")
	public String viewimg(@PathVariable Long id, @PathVariable String imageField, Model model, RedirectAttributes flash) {

		if (id > 0) {
			InstrumentoMusical instrumentoMusical = instrumentoMusicalService.findOne(id);

			if (instrumentoMusical == null) {
				flash.addFlashAttribute("error", "Data not found");
				return "redirect:/instrumentomusical/list";
			}

			model.addAttribute("instrumentoMusical", instrumentoMusical);
			fillApplicationData(model, "VIEWIMG");
			model.addAttribute("backOption",true);
			model.addAttribute("imageField",imageField);
			
			return "instrumentomusical/viewimg";
			
		}

		return "redirect:/instrumentomusical/list";
	}
	
	
	
	
	private void fillApplicationData(Model model, String screen) {
		model.addAttribute("applicationData",appData);
		model.addAttribute("optionCode",OPGEN);
		model.addAttribute("screen",screen);
	}
	
		
}
