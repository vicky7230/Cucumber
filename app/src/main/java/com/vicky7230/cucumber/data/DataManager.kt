package com.vicky7230.cucumber.data

import com.vicky7230.cucumber.data.db.DbHelper
import com.vicky7230.cucumber.data.network.ApiHelper


/**
 * Created by vicky on 31/12/17.
 */
interface DataManager : ApiHelper,DbHelper {
}