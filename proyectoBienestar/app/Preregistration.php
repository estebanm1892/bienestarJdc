<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Preregistration extends Model
{
    protected $table = 'preregistrations';
    protected $fillable = [
        'document', 'name', 'email', 'semester', 'activity_id', 'academic_program', 'readed',
    ];

    public function activities()
    {
    	return $this->belongsTo('App\activity');
    }    

}
