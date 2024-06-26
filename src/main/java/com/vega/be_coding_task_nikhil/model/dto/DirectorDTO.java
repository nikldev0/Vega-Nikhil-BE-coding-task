package com.vega.be_coding_task_nikhil.model.dto;

import java.util.UUID;

public record DirectorDTO(UUID directorId, String firstName, String lastName, String countryOfResidence, UUID companyId) {}

