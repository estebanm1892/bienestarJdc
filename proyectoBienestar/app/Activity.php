<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Activity extends Model
{
    protected $table = 'activities';
    protected $fillable = [
        'name', 'description', 'initial_hour', 'final_hour', 'area_id',
    ];

    protected $hidden = [
        'pivot'
    ];

    public function preregisters()
    {
    	return $this->hasMany('App\Preregistration');
    }

    public function area()
    {
    	return $this->belongsTo('App\Area');
    }

    public function virtual_resources()
    {
    	return $this->hasMany('App\VirtualResource');
    }

    public function days()
    {
    	return $this->belongsToMany('App\Day', 'activities_has_activity_days', 'activities_id', 'activity_days_id')->withTimestamps();
    }

    public static function unreadRegisters(){ 
        $preregisters = Preregistration::whereNull('readed', false)->get();
        return count($preregisters);
    }
}
