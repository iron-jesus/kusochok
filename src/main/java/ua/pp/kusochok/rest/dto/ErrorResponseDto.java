package ua.pp.kusochok.rest.dto;

public class ErrorResponseDto {
    private String error;

    public ErrorResponseDto(String error) {
        this.error = error;
    }

    public ErrorResponseDto() {
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
