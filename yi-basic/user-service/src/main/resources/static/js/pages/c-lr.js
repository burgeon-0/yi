define(["c-conf", "c-yi-remote", "c-lock"], function(conf, yiRemote, cLock) {

    "use strict";

    $(function() {
        $("#send-code").width($("#send-code").width());
    });

    $("#mobile").focus(function(event) {
        $("#mobile").removeClass("is-invalid");
        $("#mobile-feedback").text("");
    });

    $("#code").focus(function(event) {
        $("#code").removeClass("is-invalid");
        $("#code-feedback").text("");
    });

    var regMobile = /^1[3-9]\d{9}$/;
    var regCode = /^\d{6}$/;

    var lr = function() {
        this.lock = new cLock();
        this.removeErrorMessage = function() {
            $("#mobile").removeClass("is-invalid");
            $("#mobile-feedback").text("");
            $("#code").removeClass("is-invalid");
            $("#code-feedback").text("");
        };
        this.checkMobile = function() {
            if ($("#mobile").val() == "") {
                $("#mobile").addClass("is-invalid");
                $("#mobile-feedback").text("请输入手机号！");
                return false;
            }
            if (!regMobile.test($("#mobile").val())) {
                 $("#mobile").addClass("is-invalid");
                 $("#mobile-feedback").text("手机号格式不正确！");
                 return false;
            }
            return true;
        };
        this.checkCode = function() {
            if ($("#code").val() == "") {
                $("#code").addClass("is-invalid");
                $("#code-feedback").text("请输入验证码！");
                return false;
            }
            if (!regCode.test($("#code").val())) {
                 $("#code").addClass("is-invalid");
                 $("#code-feedback").text("验证码格式不正确！");
                 return false;
            }
            return true;
        };
        this.sendCode = function(type) {
            event.preventDefault();
            this.removeErrorMessage();
            if (!this.checkMobile()) return;

            if (!this.lock.tryLock()) return;
            yiRemote.post(conf.hostUserService, conf.uriMobileVerificationCode, {
                mobile: $("#mobile").val(),
                type: type
            }, function(data) {
                var seconds = 60;
                $("#send-code").text(seconds-- + "s");
                $("#send-code").addClass("disabled");
                var timer = setInterval(function() {
                    if (seconds > 0) {
                        $("#send-code").text(seconds-- + "s");
                    } else {
                        $("#send-code").text("发送验证码");
                        $("#send-code").removeClass("disabled");
                        clearInterval(timer);
                        this.lock.release();
                    }
                }, 1000);
            }, function(status, code, message) {
                $("#mobile").addClass("is-invalid");
                $("#mobile-feedback").text(message);
                this.lock.release();
            });
        };
    };

    return lr;

});