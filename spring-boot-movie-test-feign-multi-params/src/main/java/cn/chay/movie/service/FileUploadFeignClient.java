package cn.chay.movie.service;

import cn.chay.config.MultipartSupportConfig;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Chay
 * @date 2020/5/18 14:58
 */
//注意MultipartSupportConfig 只能用于上传文件的表单提交，不能用于其他post请求的提交，会报错：feign.codec.EncodeException: class cn.chay.movie.vo.User is not a type supported by this encoder.
//@FeignClient(name = "microservice-user-multi-params", configuration = MultipartSupportConfig.class)
public interface FileUploadFeignClient {
    @RequestMapping(value = "/user/upload", method = RequestMethod.POST,
            //这两个配置不能少
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //注意这里的注解是@RequestPart，而不是@RequestParam
    //因为文件大的时候，上传时间长，所以需要将Hystrix的超时时间设置长一些
    public String fileUpload(@RequestPart(value = "file") MultipartFile file);
}
