package com.dunnas.reservasalas.controller;

import com.dunnas.reservasalas.dto.RoomDto;
import com.dunnas.reservasalas.dto.SectorDto;
import com.dunnas.reservasalas.mappers.SectorMapper;
import com.dunnas.reservasalas.model.Sector;
import com.dunnas.reservasalas.services.SectorService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/sector")
public class SectorController {
    private final SectorService sectorService;
    private final SectorMapper sectorMapper;

    public SectorController(SectorService sectorService, SectorMapper sectorMapper) {
        this.sectorService = sectorService;
        this.sectorMapper = sectorMapper;
    }

    @GetMapping("/create")
    public String getCreateSectorPage(
            Model model,
            @RequestHeader(value="Referer",required = false) String referer) {

        String backUrl;

        if(referer == null && !referer.equals("/sector")) {
            backUrl = referer;
        }
        else{
            backUrl = "/home";
        }

        SectorDto createSectorDto = new SectorDto();
        RoomDto roomDto = new RoomDto();

        createSectorDto.getRooms().add(roomDto);

        model.addAttribute("backUrl", backUrl);
        model.addAttribute("sectorDto", createSectorDto);

        return "sector/create-sector";
    }

    @GetMapping("/update/{id}")
    public String getUpdateSectorPage(
            @PathVariable("id") Long id,
            Model model,
            @RequestHeader(value="Referer",required = false) String referer
    ){
        String backUrl;

        if(referer == null && !referer.equals("/sector")) {
            backUrl = referer;
        }
        else{
            backUrl = "/home";
        }

        SectorDto sectorDto = sectorMapper.setorToSectorDto(sectorService.findById(id));
        model.addAttribute("sectorDto", sectorDto);
        model.addAttribute("backUrl", backUrl);

        return "sector/create-sector";
    }

    @PostMapping("save")
    public String createSector(
            @ModelAttribute SectorDto createSectorDto,
            RedirectAttributes redirectAttributes) {

        Sector sector = sectorMapper.sectorDtoToSector(createSectorDto);
        if(sector.getId()!=null){
            sectorService.updateSector(sector);
        }
        else{
            sectorService.createSector(sector);
        }
        redirectAttributes.addFlashAttribute("Sucesso","Setor criado com sucesso");
        return "redirect:/home?view=sectors";
    }

    @PostMapping("/delete/{id}")
    public String deleteSector(@PathVariable("id") Long id) {

        sectorService.delete(id);

        return "redirect:/home?view=sectors";
    }

    @GetMapping("/entities")
    private ResponseEntity<List<SectorDto>> getSectors(){
        List<Sector> sectors = sectorService.findAll();

        List<SectorDto> sectorDtos = sectors.stream().map(
                sectorMapper::setorToSectorDto
        ).toList();

        return ResponseEntity.ok(sectorDtos);
    }
}
