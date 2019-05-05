/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * License: GNU Lesser General Public License (LGPL), version 2.1 or later.
 * See the lgpl.txt file in the root directory or <http://www.gnu.org/licenses/lgpl-2.1.html>.
 */
package org.hibernate.envers.test.support.domains.multiplerelations;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.envers.Audited;

/**
 * @author Lukasz Antoniak (lukasz dot antoniak at gmail dot com)
 */
@Entity
@Audited
public class Person implements Serializable {
	@Id
	@GeneratedValue
	private long id;

	private String name;

	@ManyToMany(cascade = {CascadeType.PERSIST})
	@JoinTable(name = "PERSON_ADDRESS",
			   joinColumns = {@JoinColumn(name = "personId", nullable = false)},
			   inverseJoinColumns = {@JoinColumn(name = "addressId", nullable = false)})
	private Set<Address> addresses = new HashSet<Address>();

	@OneToMany(mappedBy = "landlord", cascade = {CascadeType.PERSIST}, orphanRemoval = true)
	private Set<Address> ownedAddresses = new HashSet<Address>();

	public Person() {
	}

	public Person(String name) {
		this.name = name;
	}

	public Person(String name, long id) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public Set<Address> getOwnedAddresses() {
		return ownedAddresses;
	}

	public void setOwnedAddresses(Set<Address> ownedAddresses) {
		this.ownedAddresses = ownedAddresses;
	}

	@Override
	public boolean equals(Object o) {
		if ( this == o ) {
			return true;
		}
		if ( o == null || getClass() != o.getClass() ) {
			return false;
		}
		Person person = (Person) o;
		return id == person.id &&
				Objects.equals( name, person.name );
	}

	@Override
	public int hashCode() {
		return Objects.hash( id, name );
	}

	@Override
	public String toString() {
		return "Person{" +
				"id=" + id +
				", name='" + name + '\'' +
				'}';
	}
}