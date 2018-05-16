package ar.edu.itba.paw.homehelper.form;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;

public class ProfileGeneralInfo {

    private int elemId;

    private int savedImgId;

    @NotBlank
    @Size(max = 1000)
    private String generalDescription;

    private MultipartFile profilePicture;

    public String getGeneralDescription() {
        return generalDescription;
    }

    public void setGeneralDescription(String generdalDescription) {
        this.generalDescription = generdalDescription;
    }

    public int getElemId() {
        return elemId;
    }

    public void setElemId(int elemId) {
        this.elemId = elemId;
    }

    public MultipartFile getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(MultipartFile profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getSavedImgId() {
        return savedImgId;
    }

    public void setSavedImgId(int savedImgId) {
        this.savedImgId = savedImgId;
    }
}
