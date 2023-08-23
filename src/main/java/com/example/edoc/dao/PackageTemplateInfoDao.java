package com.example.edoc.dao;

import com.example.edoc.DTO.PackageTemplateBasicDTO;
import com.example.edoc.DTO.PackageTemplateInfoDTO;
import com.example.edoc.models.PackageTemplateInfo;

import java.util.List;

public interface PackageTemplateInfoDao {

	public Long save(PackageTemplateInfo packageTemplateInfo);
	
	public boolean update(PackageTemplateInfo PackageTemplateInfo);

	public boolean delete(PackageTemplateInfo PackageTemplateInfo);

	public PackageTemplateInfo findById(Long id);
	
	public List<PackageTemplateInfo> findAllPackageTemplateInfo();
	
	public List<PackageTemplateBasicDTO> getAllModuleIdsByPackageTemplateInfoId(Long packageTemlateInfoId);
	
	public boolean deleteRemovedPackageTemplatesFromPackageTemplatesInfo(List<Long> packageTemplateIds);
	
	public List<PackageTemplateBasicDTO> findAllPackageTemplates();
	
	public List<Long> fetchAllPackageTemplateIds(Long packageTemplateInfoId);
	
	public List<PackageTemplateInfoDTO> findAll();
}
