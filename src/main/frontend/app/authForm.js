import React from 'react';
import $ from 'jquery'

class AuthForm extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      form: {
        uid: "",
        name: "",
        matchingStrategy: "E",
        nameMatchValue: 100,
        gender: "Male",
        fullAddress: ""
      }
    };
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleSubmit(event) {
    event.preventDefault();

    let form = this.state.form;
    return $.ajax({
      type: "POST",
      url: "/authenticate",
      data: JSON.stringify(form),
      dataType: "json",
      contentType: "application/json",
      success: (function (response) {
        alert("SUCCESS")
      }),
      error: (function () {
        alert("ERROR");
      })
    });
  }


  handleChange(event) {
    let fieldName = event.target.name;
    let fleldVal = event.target.value;
    this.setState({form: {...this.state.form, [fieldName]: fleldVal}})
  }

  render() {
    return (
        <form onSubmit={this.handleSubmit}>
          <label>
            UID:
            <input type="text" name="uid" value={this.state.form.uid} onChange={this.handleChange.bind(this)}/>
          </label>

          <label>
            Name:
            <input type="text" name="name" value={this.state.form.name} onChange={this.handleChange.bind(this)}/>
          </label>

          <label>
            Matching Strategy:
            <select value={this.state.form.matchingStrategy} name="matchingStrategy" onChange={this.handleChange.bind(this)}>
              <option value="E">E</option>
              <option value="P">P</option>
              <option value="F">F</option>
            </select>
          </label>

          <label>
            Name matching value:
            <input type="number" value={this.state.form.nameMatchValue} name="nameMatchValue"
                   onChange={this.handleChange.bind(this)}/>
          </label>

          <label>
            Gender:
            <select value={this.state.form.gender} name="gender" onChange={this.handleChange.bind(this)}>
              <option value="Male">Male</option>
              <option value="Female">Female</option>
            </select>
          </label>

          <label>
            Full Address:
            <input type="text" name="fullAddress" value={this.state.form.fullAddress}
                   onChange={this.handleChange.bind(this)}/>
          </label>

          <input type="submit" value="Submit"/>

        </form>
    );
  }

}

export default AuthForm;