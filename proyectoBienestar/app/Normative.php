<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Normative extends Model
{
     protected $table = 'normatives';
    protected $fillable = [
        'tittle', 'document',
    ];
}
