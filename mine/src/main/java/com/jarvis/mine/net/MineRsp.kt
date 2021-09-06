package com.jarvis.mine.net

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep


/**
 * @author jinxiaodong
 * @description： mine模块的用户信息的返回数据，api的 data的解析后的数据类型
 * @date 2021/9/2
 */

@Keep
data class UserInfoRsp(
    val addr: String?,
    val alipay: String?,
    val channel: String?,
    val city: String?,
    var company: String?,
    val confirmed: Boolean,
    val desc: String?,
    val device: String?,
    val education: String?,
    val email: String?,
    val focus_it: String?,
    val follower_count: Int,
    val following_count: Int,
    val form_id: Long,
    val form_id_time: String?,
    val id: Long,
    val invite_code: String?,
    val inviter_id: Long,
    val is_teacher: Boolean,
    val job: String?,
    val label: String?,
    val login_time: String?,
    val logo_url: String?,
    val major: String?,
    val mobi: String?,
    val province: String?,
    val qq: Any?,
    val question_collect_qa: List<Any>?,
    val real_name: String?,
    val reg_app: String?,
    val reg_time: String?,
    val school: String?,
    val unique_code: String?,
    val username: String?,
    val wechat_id: String?,
    val work_years: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        TODO("qq"),
        TODO("question_collect_qa"),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(addr)
        parcel.writeString(alipay)
        parcel.writeString(channel)
        parcel.writeString(city)
        parcel.writeString(company)
        parcel.writeByte(if (confirmed) 1 else 0)
        parcel.writeString(desc)
        parcel.writeString(device)
        parcel.writeString(education)
        parcel.writeString(email)
        parcel.writeString(focus_it)
        parcel.writeInt(follower_count)
        parcel.writeInt(following_count)
        parcel.writeLong(form_id)
        parcel.writeString(form_id_time)
        parcel.writeLong(id)
        parcel.writeString(invite_code)
        parcel.writeLong(inviter_id)
        parcel.writeByte(if (is_teacher) 1 else 0)
        parcel.writeString(job)
        parcel.writeString(label)
        parcel.writeString(login_time)
        parcel.writeString(logo_url)
        parcel.writeString(major)
        parcel.writeString(mobi)
        parcel.writeString(province)
        parcel.writeString(real_name)
        parcel.writeString(reg_app)
        parcel.writeString(reg_time)
        parcel.writeString(school)
        parcel.writeString(unique_code)
        parcel.writeString(username)
        parcel.writeString(wechat_id)
        parcel.writeString(work_years)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserInfoRsp> {
        override fun createFromParcel(parcel: Parcel): UserInfoRsp {
            return UserInfoRsp(parcel)
        }

        override fun newArray(size: Int): Array<UserInfoRsp?> {
            return arrayOfNulls(size)
        }
    }
}