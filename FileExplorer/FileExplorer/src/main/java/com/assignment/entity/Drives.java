package com.assignment.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Drives {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	private String name;
	private String type;
	private Integer folders;
	private Integer files;
	private Integer parentFolderId;
	private String Path;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getFolders() {
		return folders;
	}
	public void setFolders(Integer folders) {
		this.folders = folders;
	}
	public Integer getFiles() {
		return files;
	}
	public void setFiles(Integer files) {
		this.files = files;
	}
	public Integer getParentFolderId() {
		return parentFolderId;
	}
	public void setParentFolderId(Integer parentFolderId) {
		this.parentFolderId = parentFolderId;
	}
	public String getPath() {
		return Path;
	}
	public void setPath(String path) {
		Path = path;
	}
}

