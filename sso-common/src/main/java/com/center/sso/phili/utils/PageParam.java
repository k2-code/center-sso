package com.center.sso.phili.utils;


import java.io.Serializable;

public class PageParam implements Serializable {
    private static final long serialVersionUID = 5572756594884593903L;
    private Integer pageIndex;
    private Integer pageSize;
    private String kind;
    private String roomName;
    private String buildingName;
    private String roomNo;
    private String meetType;
    private Integer currentStep;
    private String occupied;
    private Boolean enabled;
    private String roomCode;

    public PageParam() {
    }

    public Integer getPageIndex() {
        return this.pageIndex;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public String getKind() {
        return this.kind;
    }

    public String getRoomName() {
        return this.roomName;
    }

    public String getBuildingName() {
        return this.buildingName;
    }

    public String getRoomNo() {
        return this.roomNo;
    }

    public String getMeetType() {
        return this.meetType;
    }

    public Integer getCurrentStep() {
        return this.currentStep;
    }

    public String getOccupied() {
        return this.occupied;
    }

    public Boolean getEnabled() {
        return this.enabled;
    }

    public String getRoomCode() {
        return this.roomCode;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public void setMeetType(String meetType) {
        this.meetType = meetType;
    }

    public void setCurrentStep(Integer currentStep) {
        this.currentStep = currentStep;
    }

    public void setOccupied(String occupied) {
        this.occupied = occupied;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $pageIndex = this.getPageIndex();
        result = result * 59 + ($pageIndex == null ? 43 : $pageIndex.hashCode());
        Object $pageSize = this.getPageSize();
        result = result * 59 + ($pageSize == null ? 43 : $pageSize.hashCode());
        Object $currentStep = this.getCurrentStep();
        result = result * 59 + ($currentStep == null ? 43 : $currentStep.hashCode());
        Object $enabled = this.getEnabled();
        result = result * 59 + ($enabled == null ? 43 : $enabled.hashCode());
        Object $kind = this.getKind();
        result = result * 59 + ($kind == null ? 43 : $kind.hashCode());
        Object $roomName = this.getRoomName();
        result = result * 59 + ($roomName == null ? 43 : $roomName.hashCode());
        Object $buildingName = this.getBuildingName();
        result = result * 59 + ($buildingName == null ? 43 : $buildingName.hashCode());
        Object $roomNo = this.getRoomNo();
        result = result * 59 + ($roomNo == null ? 43 : $roomNo.hashCode());
        Object $meetType = this.getMeetType();
        result = result * 59 + ($meetType == null ? 43 : $meetType.hashCode());
        Object $occupied = this.getOccupied();
        result = result * 59 + ($occupied == null ? 43 : $occupied.hashCode());
        Object $roomCode = this.getRoomCode();
        result = result * 59 + ($roomCode == null ? 43 : $roomCode.hashCode());
        return result;
    }

    public String toString() {
        return "PageParam(pageIndex=" + this.getPageIndex() + ", pageSize=" + this.getPageSize() + ", kind=" + this.getKind() + ", roomName=" + this.getRoomName() + ", buildingName=" + this.getBuildingName() + ", roomNo=" + this.getRoomNo() + ", meetType=" + this.getMeetType() + ", currentStep=" + this.getCurrentStep() + ", occupied=" + this.getOccupied() + ", enabled=" + this.getEnabled() + ", roomCode=" + this.getRoomCode() + ")";
    }
}
