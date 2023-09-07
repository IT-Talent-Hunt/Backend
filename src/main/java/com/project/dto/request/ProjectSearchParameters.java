package com.project.dto.request;

public record ProjectSearchParameters(String[] status, String[] specialities,
                                      String[] teamSize, String[] name) {
}
