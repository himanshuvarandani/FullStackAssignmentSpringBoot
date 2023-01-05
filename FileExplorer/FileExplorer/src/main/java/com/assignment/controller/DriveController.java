package com.assignment.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.assignment.entity.Drives;
import com.assignment.repository.DriveRepository;


@Controller
public class DriveController {
	@Autowired
	private DriveRepository driveRepository;
	
	@GetMapping("/assignment/drives")
	public String getDrives(Model model) {
		List<Drives> drives = driveRepository.findAllByType("drive");
		
		model.addAttribute("drives", drives);
		
		return "drives";
	}

	@GetMapping("/assignment/folder/{folderId}")
	public String getFolderDetails(@PathVariable("folderId") Integer folderId, Model model) {
		Optional<Drives> folderDetails = driveRepository.findById(folderId);
		
		if (folderDetails.isEmpty()) {
			return null;
		}
		List<Drives> folderChildren = driveRepository.findAllByParentFolderId(folderId);
		
		model.addAttribute("folderChildren", folderChildren);
		model.addAttribute("folderDetails", folderDetails.get());
		
		return "folder";
	}
}
