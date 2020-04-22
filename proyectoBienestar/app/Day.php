<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Day extends Model
{
    protected $table = 'activity_days';
    protected $fillable = [
        'name',
    ];

    protected $hidden = [
        'pivot'
    ];

    public function activities()
    {
    	return $this->belongsToMany('App\Activity', 'activities_has_activity_days', 'activity_days_id', 'activities_id')->withTimestamps();
    }
}
