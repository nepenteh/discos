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
import com.ejerciciosmesa.discos.models.entities.Banda;
import com.ejerciciosmesa.discos.models.services.BandaService;




@Controller
@SessionAttributes("banda")
@RequestMapping("/banda")
public class BandaController {

	private final AppData appData;
	private final BandaService bandaService;
	
	
	
	
	
	private final UploadService uploadService;

		
	public static final String OPGEN = "BANDA"; 
	
	public BandaController(UploadService uploadService,
										 
										 
									     BandaService bandaService,
									     AppData applicationData
		   
		   		 
			) {
		this.appData = applicationData;
		this.bandaService = bandaService;
		
		
		

		this.uploadService = uploadService;

	}

		
	
	@GetMapping({"","/","/list"})
	public String list(@RequestParam(name="page", defaultValue="0") int page, Model model) {
	
		fillApplicationData(model,"LIST");
		
		Pageable pageRequest = PageRequest.of(page, 10);
		Page<Banda> pageBanda = bandaService.findAll(pageRequest); 
		PageRender<Banda> paginator = new PageRender<>("/banda/list",pageBanda,5);
		

		model.addAttribute("numbanda", bandaService.count());
		model.addAttribute("listbanda", pageBanda);
		model.addAttribute("paginator",paginator);
		
		return "banda/list";
	}
	
	@GetMapping("/form")
	public String form(Model model) {
		Banda banda = new Banda();		
		model.addAttribute("banda",banda);
		
		fillApplicationData(model,"CREATE");
		
		return "banda/form";
	}
	
	@GetMapping("/form/{id}")
	public String form(@PathVariable Long id, Model model, RedirectAttributes flash) {
		Banda banda = bandaService.findOne(id);
		if(banda==null) {
			flash.addFlashAttribute("error","Data not found");
			return "redirect:/banda/list";
		}
		
		model.addAttribute("banda", banda);
		
		fillApplicationData(model,"UPDATE");
		
		return "banda/form";
	}
	
	
	@PostMapping("/form")
	@Secured("ROLE_ADMIN")
	public String form(@Valid Banda banda,  
			           BindingResult result, 
					   
					   Model model,
					   @RequestAttribute("file") MultipartFile foto_formname,
@RequestParam("fotoImageText") String fotoImageText,
@RequestParam("fotoImageTextOld") String fotoImageTextOld,

					   RedirectAttributes flash,
					   SessionStatus status) {
		
		if(banda.getId()==null)
			fillApplicationData(model,"CREATE");
		else
			fillApplicationData(model,"UPDATE");
		
		String msg = (banda.getId()==null) ? "Creation successful" : "Update successful";
		
		if(result.hasErrors()) {
			return "banda/form";
		}
		
		if(!foto_formname.isEmpty())
	AddUpdateImageFoto(foto_formname,banda);
else {
	if(fotoImageText.isEmpty() && !(fotoImageTextOld.isEmpty())) {
		uploadService.delete(fotoImageTextOld);
		banda.setFoto(null);
	}
}



		
		
		
		bandaService.save(banda);
		status.setComplete();
		flash.addFlashAttribute("success",msg);
		return "redirect:/banda/list";
	}
	
	
	private void AddUpdateImageFoto(MultipartFile image, Banda banda) {
					
			if(banda.getId()!=null &&
			   banda.getId()>0 && 
			   banda.getFoto()!=null &&
			   banda.getFoto().length() > 0) {
			
				uploadService.delete(banda.getFoto());
			}
			
			String uniqueName = null;
			try {
				uniqueName = uploadService.copy(image);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			banda.setFoto(uniqueName);
		
	}

	

	@Secured("ROLE_ADMIN")
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Long id, RedirectAttributes flash) {
		
		if(id>0) { 			
			Banda banda = bandaService.findOne(id);
			
			if(banda != null) {
				
		/* Only if there is required relation with this entity */			
				
		
		/* Only if there is no required relation with this entity, or there is a
		 * required relation but no entity related at the moment, (checked before) */
				
		
		/* Relations revised, the entity can be removed */
				bandaService.remove(id);
			} else {
				flash.addFlashAttribute("error","Data not found");
				return "redirect:/banda/list";
			}
			
			if(banda.getFoto()!=null)
	uploadService.delete(banda.getFoto());

						
			flash.addFlashAttribute("success","Deletion successful");
		}
		
		return "redirect:/banda/list";
	}
	
	@GetMapping("/view/{id}")
	public String view(@PathVariable Long id, Model model, RedirectAttributes flash) {

		if (id > 0) {
			Banda banda = bandaService.findOne(id);

			if (banda == null) {
				flash.addFlashAttribute("error", "Data not found");
				return "redirect:/banda/list";
			}

			model.addAttribute("banda", banda);
			fillApplicationData(model, "VIEW");
			return "banda/view";
			
		}

		return "redirect:/banda/list";
	}
	
	
	@GetMapping("/viewimg/{id}/{imageField}")
	public String viewimg(@PathVariable Long id, @PathVariable String imageField, Model model, RedirectAttributes flash) {

		if (id > 0) {
			Banda banda = bandaService.findOne(id);

			if (banda == null) {
				flash.addFlashAttribute("error", "Data not found");
				return "redirect:/banda/list";
			}

			model.addAttribute("banda", banda);
			fillApplicationData(model, "VIEWIMG");
			model.addAttribute("backOption",true);
			model.addAttribute("imageField",imageField);
			
			return "banda/viewimg";
			
		}

		return "redirect:/banda/list";
	}
	
	
	
	
	private void fillApplicationData(Model model, String screen) {
		model.addAttribute("applicationData",appData);
		model.addAttribute("optionCode",OPGEN);
		model.addAttribute("screen",screen);
	}
	
		
}
