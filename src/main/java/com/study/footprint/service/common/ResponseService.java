package com.study.footprint.service.common;

import com.study.footprint.common.response.CommonResult;
import com.study.footprint.common.response.ListResult;
import com.study.footprint.common.response.SingleResult;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ResponseService {

    private final MessageSource messageSource;

    public ResponseService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * 단일 결과 리턴해주는 리스트
     * @param data
     * @return
     * @param <T>
     */
    public <T> SingleResult<T> getSingleResult(T data) {
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    /**
     * 다중 결과 리턴해주는 리스트
     * @param list
     * @return
     * @param <T>
     */
    public <T>ListResult<T> getListResult(List<T> list) {
        ListResult<T> result = new ListResult<>();
        result.setList(list);
        setSuccessResult(result);
        return result;
    }

    public CommonResult getFailResult(Exception e) {
        CommonResult result = new CommonResult();
        result.setCode(Integer.parseInt(messageSource.getMessage(e.getMessage() + ".code", null, LocaleContextHolder.getLocale())));
        result.setMsg(messageSource.getMessage(e.getMessage() + ".msg", null, LocaleContextHolder.getLocale()));
        return result;
    }
    /**
     * 결과 모델에 api 요청 성공 데이터를 세팅해주는 메소드
     * @param result
     */
    private void setSuccessResult(CommonResult result) {
        result.setCode(Integer.parseInt(messageSource.getMessage("successResult.code", null, LocaleContextHolder.getLocale())));
        result.setMsg(messageSource.getMessage("successResult.msg", null, LocaleContextHolder.getLocale()));
    }
}
