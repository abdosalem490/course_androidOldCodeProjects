package com.abdosalm.parks.data;

import com.abdosalm.parks.model.Park;

import java.util.List;

public interface AsyncResponse {
    void processPark(List<Park> parks);

}
