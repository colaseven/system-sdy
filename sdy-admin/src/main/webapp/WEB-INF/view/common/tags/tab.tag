
<div class="form-group">
    <label class="col-sm-3 control-label">${name}</label>
    <div class="col-sm-9">
        <ul id="myTab" class="nav nav-tabs">
            <li class="active">
                <a href="#detail" data-toggle="tab">图标</a>
            </li>
            <li><a href="#banner" data-toggle="tab">详情Banner</a></li>

            <li><a href="#bannerIndex" data-toggle="tab">首页Banner</a></li>
        </ul>
        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade in active" id="detail" style="padding-top: 10px;">
                <p>
                    <span style="color: #ff795c!important">注：</span>图标为必填选项，只可上传单张。尺寸为100*100像素，格式为jpg、png、jpeg，用于呈现品牌信息的图标，兑换记录等页面。</p>
                @if(isNotEmpty("${item.logoPic}")){
                    <#upload id="logoPic" bannerImg="${item.logoPic}"/>
                @}else{
                    <#upload/>
                @}
            </div>
            <div class="tab-pane fade" id="banner">
                <div id="uploader" style="padding-top: 10px;">
                    <p><span style="color: #ff795c!important">注：</span>详情图为必填选项，可以上传单张多张。尺寸为640*300像素或750*350像素，格式为jpg、png、jpeg，图片大小不可大于1M。</p>
                    <!--用来存放item-->
                    <div class="queueList filled">
                    </div>
                    <div class="form-group" id="uploaderGroup">
                        <div id="filePicker" style="display: inline">选择图片</div>
                        <#button id="ctlBtn" icon="fa-upload" name="开始上传" clickFun="GoodsInfoDlg.upload()" space="true" style="vertical-align : top"/>
                    </div>
                </div>
            </div>
            <div class="tab-pane fade" id="bannerIndex" style="padding-top: 10px;">
                <p><span style="color: #ff795c!important">注：</span>展示在首页Banner区的图片，格式为jpg、png、jpeg，图片大小不可大于1M。</p>
                @if(isNotEmpty("${item.indexBannerPic}")){
                    <#upload id="indexBannerPic" bannerImg="${item.indexBannerPic}"/>
                @}else{
                    <#upload/>
                @}
            </div>
        </div>
    </div>
</div>