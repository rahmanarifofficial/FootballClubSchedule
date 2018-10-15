package com.rahmanarif.footballclubschedule.model

import com.google.gson.annotations.SerializedName

data class Team(
        @SerializedName("idTeam")
        var teamId: String? = null,

        @SerializedName("strTeamBadge")
        var teamBadge: String? = null
)