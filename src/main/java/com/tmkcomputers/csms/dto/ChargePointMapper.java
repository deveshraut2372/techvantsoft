package com.tmkcomputers.csms.dto;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChargePointMapper {

    public static ChargePointDTO mapJsonToChargePointDTO(JSONObject stationObject) {
        ChargePointDTO stationDTO = new ChargePointDTO();
        
        // Mapping simple fields
        stationDTO.setId(stationObject.getInt("ID"));
        stationDTO.setDateCreated(stationObject.optString("DateCreated", null));
        stationDTO.setNumberOfPoints(stationObject.optInt("NumberOfPoints", 0));
        stationDTO.setUsageTypeId(stationObject.optInt("UsageTypeID", 0));

        // Mapping nested object: UsageType
        if (!stationObject.isNull("UsageType")) {
            JSONObject usageTypeObject = stationObject.getJSONObject("UsageType");
            UsageType usageType = new UsageType();
            usageType.setIsMembershipRequired(usageTypeObject.optBoolean("IsMembershipRequired"));
            usageType.setIsPayAtLocation(usageTypeObject.optBoolean("IsPayAtLocation"));
            usageType.setTitle(usageTypeObject.optString("Title"));
            usageType.setId(usageTypeObject.optInt("ID"));
            stationDTO.setUsageType(usageType);
        }

        // Mapping array: Connections
        if (!stationObject.isNull("Connections")) {
            JSONArray connectionsArray = stationObject.getJSONArray("Connections");
            List<Connection> connections = new ArrayList<>();
            
            for (int j = 0; j < connectionsArray.length(); j++) {
                JSONObject connectionObject = connectionsArray.getJSONObject(j);
                Connection connection = new Connection();
                
                // Map simple fields
                connection.setId(connectionObject.optInt("ID", 0));
                connection.setCurrentTypeId(connectionObject.optInt("CurrentTypeID", 0));
                connection.setComments(connectionObject.optString("Comments", null));
                connection.setPowerKW(connectionObject.optDouble("PowerKW", 0.0));
                connection.setQuantity(connectionObject.optInt("Quantity", 0));
                connection.setAmps(connectionObject.optInt("Amps", 0));
                connection.setVoltage(connectionObject.optInt("Voltage", 0));
                
                // Map nested object: ConnectionType
                if (!connectionObject.isNull("ConnectionType")) {
                    JSONObject connectionTypeObject = connectionObject.getJSONObject("ConnectionType");
                    ConnectionType connectionType = new ConnectionType();
                    connectionType.setIsDiscontinued(connectionTypeObject.optBoolean("IsDiscontinued", false));
                    connectionType.setFormalName(connectionTypeObject.optString("FormalName", null));
                    connectionType.setIsObsolete(connectionTypeObject.optBoolean("IsObsolete", false));
                    connectionType.setTitle(connectionTypeObject.optString("Title", null));
                    connectionType.setId(connectionTypeObject.optInt("ID", 0));
                    connection.setConnectionType(connectionType);
                }
                
                // Map nested object: CurrentType
                if (!connectionObject.isNull("CurrentType")) {
                    JSONObject currentTypeObject = connectionObject.getJSONObject("CurrentType");
                    CurrentType currentType = new CurrentType();
                    currentType.setDescription(currentTypeObject.optString("Description", null));
                    currentType.setTitle(currentTypeObject.optString("Title", null));
                    currentType.setId(currentTypeObject.optInt("ID", 0));
                    connection.setCurrentType(currentType);
                }

                // Map nested object: Level
                if (!connectionObject.isNull("Level")) {
                    JSONObject levelObject = connectionObject.getJSONObject("Level");
                    Level level = new Level();
                    level.setIsFastChargeCapable(levelObject.optBoolean("IsFastChargeCapable", false));
                    level.setComments(levelObject.optString("Comments", null));
                    level.setTitle(levelObject.optString("Title", null));
                    level.setId(levelObject.optInt("ID", 0));
                    connection.setLevel(level);
                }

                connections.add(connection);
            }
            stationDTO.setConnections(connections);
        }

        // Mapping nested object: AddressInfo
        if (!stationObject.isNull("AddressInfo")) {
            JSONObject addressObject = stationObject.getJSONObject("AddressInfo");
            AddressInfo addressInfo = new AddressInfo();
            addressInfo.setCountryId(addressObject.optInt("CountryID", 0));
            addressInfo.setContactTelephone1(addressObject.optString("ContactTelephone1", null));
            addressInfo.setStateOrProvince(addressObject.optString("StateOrProvince", null));
            addressInfo.setTitle(addressObject.optString("Title", null));
            addressInfo.setLatitude(addressObject.optDouble("Latitude", 0.0));
            addressInfo.setPostcode(addressObject.optString("Postcode", null));
            addressInfo.setLongitude(addressObject.optDouble("Longitude", 0.0));
            addressInfo.setAccessComments(addressObject.optString("AccessComments", null));
            addressInfo.setAddressLine1(addressObject.optString("AddressLine1", null));
            addressInfo.setTown(addressObject.optString("Town", null));

            // Map nested object: Country
            if (!addressObject.isNull("Country")) {
                JSONObject countryObject = addressObject.getJSONObject("Country");
                Country country = new Country();
                country.setIsoCode(countryObject.optString("ISOCode", null));
                country.setTitle(countryObject.optString("Title", null));
                country.setId(countryObject.optInt("ID", 0));
                country.setContinentCode(countryObject.optString("ContinentCode", null));
                addressInfo.setCountry(country);
            }
            stationDTO.setAddressInfo(addressInfo);
        }

        // Mapping DataProvider (nested object)
    if (!stationObject.isNull("DataProvider")) {
        JSONObject dataProviderObject = stationObject.getJSONObject("DataProvider");
        DataProvider dataProvider = new DataProvider();
        dataProvider.setIsRestrictedEdit(dataProviderObject.optBoolean("IsRestrictedEdit", false));
        dataProvider.setComments(dataProviderObject.optString("Comments", null));
        dataProvider.setWebsiteUrl(dataProviderObject.optString("WebsiteURL", null));
        dataProvider.setLicense(dataProviderObject.optString("License", null));
        dataProvider.setTitle(dataProviderObject.optString("Title", null));
        dataProvider.setId(dataProviderObject.optInt("ID", 0));
        dataProvider.setIsApprovedImport(dataProviderObject.optBoolean("IsApprovedImport", false));
        dataProvider.setDateLastImported(dataProviderObject.optString("DateLastImported", null));
        dataProvider.setIsOpenDataLicensed(dataProviderObject.optBoolean("IsOpenDataLicensed", false));

        // Mapping nested DataProviderStatusType
        if (!dataProviderObject.isNull("DataProviderStatusType")) {
            JSONObject dataProviderStatusTypeObject = dataProviderObject.getJSONObject("DataProviderStatusType");
            DataProviderStatusType dataProviderStatusType = new DataProviderStatusType();
            dataProviderStatusType.setTitle(dataProviderStatusTypeObject.optString("Title", null));
            dataProviderStatusType.setId(dataProviderStatusTypeObject.optInt("ID", 0));
            dataProviderStatusType.setIsProviderEnabled(dataProviderStatusTypeObject.optBoolean("IsProviderEnabled", false));
            dataProvider.setDataProviderStatusType(dataProviderStatusType);
        }
        stationDTO.setDataProvider(dataProvider);
    }

    // Mapping StatusType (nested object)
    if (!stationObject.isNull("StatusType")) {
        JSONObject statusTypeObject = stationObject.getJSONObject("StatusType");
        StatusType statusType = new StatusType();
        statusType.setIsUserSelectable(statusTypeObject.optBoolean("IsUserSelectable", false));
        statusType.setIsOperational(statusTypeObject.optBoolean("IsOperational", false));
        statusType.setTitle(statusTypeObject.optString("Title", null));
        statusType.setId(statusTypeObject.optInt("ID", 0));
        stationDTO.setStatusType(statusType);
    }

        return stationDTO;
    }

    public static List<ChargePointDTO> mapJsonArrayToChargePointDTOList(JSONArray stationsArray) {
        List<ChargePointDTO> stationDTOs = new ArrayList<>();
        
        for (int i = 0; i < stationsArray.length(); i++) {
            JSONObject stationObject = stationsArray.getJSONObject(i);
            ChargePointDTO stationDTO = mapJsonToChargePointDTO(stationObject);
            stationDTOs.add(stationDTO);
        }
        
        return stationDTOs;
    }
}

