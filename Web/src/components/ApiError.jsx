import React from 'react'
import stuart from '../stuart.png'
const ApiError = () => {

  return (
    
    <div className='invalid'>
      <h1>OOPS, SERVER DOWN!</h1>
      <h5>But don't worry, it's me, not you</h5>
      <img className='meme' src={stuart} alt='stuart'/>
 
      
    </div>
  )
}
export default ApiError;