<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Area extends Model
{
    protected $table = 'areas';
    protected $fillable = [
        'name', 'area_image', 'area_presentation', 'objetive', 'programs',
    ];

    public function users()
    {
    	return $this->hasMany('App\User');
    }

    public function activities()
    {
    	return $this->hasMany('App\Activity');
    }

    public function publications()
    {
    	return $this->hasMany('App\Publication');
    }
}
