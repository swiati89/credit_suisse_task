package converter;

import dto.LogDataDTO;
import model.EventData;

public class EvenDataConverter {
    public EventData convertLogDataDtoToEventDataWithDurationTime(LogDataDTO logDataDTO, Long time){
        EventData eventData = new EventData();
        if (logDataDTO.getHost() != null){
            eventData.setHost(logDataDTO.getHost());
        }
        if(logDataDTO.getType() != null){
            eventData.setType(logDataDTO.getType());
        }
        eventData.setEventId(logDataDTO.getId());
        eventData.setDuration(time);
        eventData.setAlert(time > 4);

        return eventData;

    }
}
