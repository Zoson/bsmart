package com.koalitech.bsmart.domain.remote;

import com.koalitech.service.enity.Enity;

/**
 * Created by Zoson on 2016/10/5.
 */
public class HttpResponse extends Enity {
    int status;
    String message;
    String data;
}
