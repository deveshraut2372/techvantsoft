package com.tmkcomputers.csms.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ChargePointDTO {
    private List<Connection> connections;
    private Integer numberOfPoints;
    private Integer usageTypeId;
    private String dateCreated;
    private Integer operatorId;
    private UsageType usageType;
    private Integer submissionStatusTypeId;
    private DataProvider dataProvider;
    private SubmissionStatus submissionStatus;
    private Integer dataQualityLevel;
    private Boolean isRecentlyVerified;
    private String dateLastStatusUpdate;
    private Integer operatorInfo;
    private Double percentageSimilarity;
    private Integer id;
    private String uuid;
    private Integer statusTypeId;
    private String dateLastVerified;
    private String usageCost;
    private String userComments;
    private String datePlanned;
    private String generalComments;
    private String mediaItems;
    private String dataProvidersReference;
    private String metadataValues;
    private String parentChargePointId;
    private AddressInfo addressInfo;
    private String operatorsReference;
    private String dateLastConfirmed;
    private StatusType statusType;
    private Integer dataProviderId;
}

@Getter
@Setter
@NoArgsConstructor
class Connection {
    private Integer currentTypeId;
    private ConnectionType connectionType;
    private String reference;
    private String comments;
    private Double powerKW;
    private Integer quantity;
    private Integer amps;
    private CurrentType currentType;
    private String statusType;
    private Integer voltage;
    private Level level;
    private Integer id;
    private Integer connectionTypeId;
    private Integer statusTypeId;
    private Integer levelId;
}

@Getter
@Setter
@NoArgsConstructor
class ConnectionType {
    private Boolean isDiscontinued;
    private String formalName;
    private Boolean isObsolete;
    private String title;
    private Integer id;
}

@Getter
@Setter
@NoArgsConstructor
class CurrentType {
    private String description;
    private String title;
    private Integer id;
}

@Getter
@Setter
@NoArgsConstructor
class Level {
    private Boolean isFastChargeCapable;
    private String comments;
    private String title;
    private Integer id;
}

@Getter
@Setter
@NoArgsConstructor
class UsageType {
    private Boolean isMembershipRequired;
    private Boolean isPayAtLocation;
    private String title;
    private Boolean isAccessKeyRequired;
    private Integer id;
}

@Getter
@Setter
@NoArgsConstructor
class DataProvider {
    private Boolean isRestrictedEdit;
    private String comments;
    private String websiteUrl;
    private String license;
    private String title;
    private Integer id;
    private DataProviderStatusType dataProviderStatusType;
    private Boolean isApprovedImport;
    private String dateLastImported;
    private Boolean isOpenDataLicensed;
}

@Getter
@Setter
@NoArgsConstructor
class DataProviderStatusType {
    private String title;
    private Integer id;
    private Boolean isProviderEnabled;
}

@Getter
@Setter
@NoArgsConstructor
class SubmissionStatus {
    private String title;
    private Integer id;
    private Boolean isLive;
}

@Getter
@Setter
@NoArgsConstructor
class AddressInfo {
    private Integer countryId;
    private String contactTelephone2;
    private String contactTelephone1;
    private String stateOrProvince;
    private String title;
    private Double latitude;
    private String postcode;
    private Double longitude;
    private String accessComments;
    private String addressLine2;
    private String addressLine1;
    private String town;
    private String relatedUrl;
    private Country country;
    private String contactEmail;
    private Integer id;
    private Integer distanceUnit;
    private Double distance;
}

@Getter
@Setter
@NoArgsConstructor
class Country {
    private String isoCode;
    private String title;
    private Integer id;
    private String continentCode;
}

@Getter
@Setter
@NoArgsConstructor
class StatusType {
    private Boolean isUserSelectable;
    private Boolean isOperational;
    private String title;
    private Integer id;
}

