package com.springapp.mvc.dto;



public class DeleteUserDto {


    private Long deletedUserId;

    public Long getDeletedUserId() {
        return deletedUserId;
    }

    public void setDeletedUserId(Long deletedUserId) {
        this.deletedUserId = deletedUserId;
    }

    public DeleteUserDto(Long deletedUserId) {
        this.deletedUserId = deletedUserId;
    }
}
