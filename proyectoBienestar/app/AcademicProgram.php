<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class AcademicProgram extends Model
{
    protected $table = 'academic_programs';
    protected $fillable = [
        'name',
    ];

    public function preregister()
    {
    	return $this->hasMany('App\Preregistration');
    }
}
